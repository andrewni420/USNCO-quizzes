import React, {Component} from 'react'
import Question from './Question'
import Sidebar from './Sidebar'
import {getTopic,getTest} from './AnswerFormats'

class Quiz extends Component {

    state = {
        PTable:"hide",
        exclude:[{year:2020,test:"N"}],
    };

    constructor(props){
      super(props);
      const {topic,exclude}=this.props;
      //topic=[[1,0],[1,1],[2,1],etc.], exclude=[{year:2020,test:"L"},{year:2019,test:"N"},etc]
      this.state.questions=[];
      this.state.topic=this.getNames(topic,exclude)
      this.state.topicquestions=this.getQuestions(this.trimTopics(this.props.topic));
    }  

    trimTopics = (topics) => {
      let supertopics=[],i=0;
      let special=null;
      topics.forEach((v) => {
        let a=v[0],b=v[1];
        if (a===8&&b===0) special=v;
        if ( (a===0&&b===1) || (a!==0&&a!==1&&a!==4&&b===0) || (a===8&&b===2)) supertopics[i++]=v;
      })

      let newtopics=topics.filter((v) => {
        let keep=true;
        let a=v[0],b=v[1];
        if (special!=null && a===8 && b===1) keep = false;
        supertopics.forEach((topic) => {
          let x=topic[0],y=topic[1];
          if (a===x&&b>y) {
            keep = false;//why can't I return here...???
          }
        });
        return (keep);
      });
      return newtopics;
    }

    componentDidMount(){
      this.randomQuestion(false);
    }

    getNames = (topic,exclude) => {
      const names= topic.map((t,i) => {
        let and = " AND ";
        if (i===topic.length-1) and="";
        return (getTopic(t).name+and);
      });
      const excluded = exclude.map((t) => {
        let test="Local";
        if (t.test==="N")test="USNCO";
        return (" NOT "+t.year+" "+test);
      })

        return <div className = "topicNames">{names} {excluded}</div>
    }
    
    getQuestions = (topic) => {
      const topics = topic.map(t => getTopic(t).topic);
      let index=0;
      let questions=[];

      let n = topics.length;
      for (let i=0;i<n;i++){
        
        let m=topics[i].length;
        for (let j=0;j<m;j++){
          let o=topics[i][j].length;
            let test=getTest(j,true);
            let skip=false;
            this.props.exclude.forEach(element => {
              if (test.year===element.year&&test.test===element.test) skip=true;
            });

            if (!skip){
              for (let k=0;k<o;k++){
              questions[index]={year:test.year,number:topics[i][j][k],test:test.test};
              index++;
            }
          }
        }
      }
      return questions;
    }

    isExcluded = (year,test) => {
      const { exclude }=this.props;
      for (let i=0;i<exclude.length;i++){
      if (year===exclude[i].year&&test===exclude[i].test)return true;
      }
      return false;
    }

    randomQuestion = (keepprevious) => {
      let n = this.state.topicquestions.length;
      if (n===0) {
        this.noMoreQuestions();
        return;
      }
      let i = Math.floor(Math.random()*n);
      let questions;
      if (keepprevious) questions=[...this.state.questions,this.state.topicquestions[i]];
      else questions=[this.state.topicquestions[i]];
      this.setState({
        questions: questions,
        topicquestions:this.state.topicquestions.filter((q,index) => { return index!==i})});
    }


    noMoreQuestions = () => {
      window.alert("No more questions")
    }

    showPTable = () => {
      if (this.state.PTable==="hide") this.setState({PTable:""});
      else this.setState({PTable:"hide"})
    }

    showAll = () => {
      this.setState({
        questions: this.getQuestions(this.props.topic),
        topicquestions : []
      })
    }

    hideQuestion = (index) => {
      const Qslice = this.state.questions.slice();
      this.setState({questions:Qslice.filter((v,i)=> i!==index)})
  }
    
    render() {
        const {topic} = this.state;

        return (
        <div>
            <div className = "row">

                <div className = "header quiz">
                {topic}
                </div>
            </div>

            <div className = "row">
                <div className = "buttons column left">
                <Sidebar randomQuestion = {this.randomQuestion} showAll = {this.showAll} showPTable = {this.showPTable}/>
                </div>

                <div className = "column main">
                {this.state.questions.map((q,i) => {return (
                  <Question 
                    data={q} 
                    key={q.year+" "+q.test+" "+q.number}
                    hide={() => this.hideQuestion(i)}
                    />);})}
                {/* <Table characterData = {questions} removeCharacter = {this.removeCharacter}/>
                <Form handleSubmit={this.handleSubmit} /> */}
                </div>

                <div className = {"column ptable "+this.state.PTable}>
                  <img className = {"ptableimg "+this.state.PTable} src={process.env.PUBLIC_URL + "/PTable.png"} alt="Periodic Table"/>
                </div>
            </div>
        </div>
        )
    }
}

export default Quiz

import React, {Component} from 'react'
import {NavLink} from 'react-router-dom'
import {topicnames,topicnumbers,formats,getTest} from './AnswerFormats'

const Checkbox = (props) => {
                const {topic,checked,onClick,newline,className} = props;
                return(
                <React.Fragment>
                    <input type="checkbox" 
                            key={topic}
                            checked={checked} 
                            onClick = {onClick} 
                            onChange = {() => {}}/>
                    <label className={className}>{topic}</label>
                    {newline}
                </React.Fragment>);
}

class Form extends Component {

    state = {

    }

    constructor(props){
        super(props);
        this.state.topicChecked=[];
        for (let i=0;i<topicnames.length;i++){
            this.state.topicChecked[i]=false;
        }
        this.state.testChecked=[];
        for (let i=0;i<formats.length;i++){
            this.state.testChecked[i]=true;
        }
        this.state.clicked=-1;
    }

    componentDidUpdate(){
        const {clicked}=this.state;
        if (clicked!==-1) {
            //this.consistentChecks(this.state.clicked);
            this.setState({clicked:-1});
        }
    }
    
    reset = () => {
        let checked=[];
        for (let i=0;i<topicnames.length;i++){
            checked[i]=false;
        }
        this.setState({topicChecked:checked});
    }
    submitForm = () => {
        let i=0;
        let topics=[];
        this.state.topicChecked.forEach((checked,index) => {
            if (checked) {
                const row = this.getRow(index);
                topics[i]=[row,index-topicnumbers[row]];
                i++;
            }
        });

        let excluded=[];
        this.state.testChecked.forEach((checked,index) => {
            if (!checked) {
                excluded[i]=getTest(index,true);
                i++;
            }
        });

        this.props.handleSubmit(topics,excluded);/////////////////

        this.reset();
    }

    checkBox = (index,isTopic) => {
        let name = "topicChecked"
        if (!isTopic) name = "testChecked";

        const checkSlice = this.state[name].slice();
        checkSlice[index]=!checkSlice[index];
        let state={};
        state[name]=checkSlice;
        state["clicked"]=index;
        this.setState(state);
    }

    getRow = (index) => {
        let row = -1;
        for (let i=0;i<topicnumbers.length;i++){
            if (index<topicnumbers[i]) {
                row=i-1;
                break;
            }
        }
        return row;
    }
    isBigButton = (index) => {
        let bigbutton=false;
        for (let i=0;i<topicnumbers.length;i++){
            if (index===topicnumbers[i]) bigbutton=true;
        }
        return bigbutton;
    }
    consistentChecks = (index) => {
        const row=this.getRow(index);
        const bigbutton=this.isBigButton(index);
        const low = topicnumbers[row];
        const high = topicnumbers[row+1];
        const checkSlice = this.state.topicChecked.slice();

        if (bigbutton) {
            for (let i=low;i<high;i++){
                checkSlice[i]=checkSlice[low];
            }
        }
        else {
            let checked=true;
            for (let i=low+1;i<high;i++){
                if (!checkSlice[i]) checked=false;
            }
            checkSlice[low]=checked;
        }
        this.setState({topicChecked:checkSlice,clicked:index});
    }
    toggleTests = () => {
        this.setState((state)=>{
            const tests=state.testChecked.every(v=>v);
            const checked=state.testChecked.map(v=>!tests);
            return ({testChecked:checked}); 
        })
    }
    toggleTopics = () => {
        this.setState((state)=>{
            const topics=state.topicChecked.every(v=>v);
            const checked=state.topicChecked.map(v=>!topics);
            return ({topicChecked:checked}); 
        })
    }

    render () {
        let i=1;
        const topics = topicnames.map((topic,index) => {
            const numbers=topicnumbers.slice(0,-1);
            const question = "Questions "+(6*i+1)+"-"+6*(i+1);
                if (numbers.includes(index+1)) i++;
            const newline=numbers.includes(index+1) ? <React.Fragment><br/><label className = "topicLabel l0">{question}</label></React.Fragment> : null;
            const first = index===0 ? <label className="topicLabel l0">Questions 1-6</label> : null;
            return (<React.Fragment key={topic}>
                    {first}
                    <Checkbox 
                        topic={topic} 
                        checked={this.state.topicChecked[index]} 
                        onClick={() => {this.checkBox(index,true);}} 
                        newline={newline}
                        className = {"topicLabel l"+(1+index-topicnumbers[this.getRow(index)])}
                        />
                    </React.Fragment>
            )
        });

        let tests=[];
        for (let i=0;i<formats.length/2;i++){
            const year=getTest(2*i).year;
            tests[i] = <div key={year}>
            <Checkbox key={year+" Locals"}
                topic={year+" Locals"} 
                checked={this.state.testChecked[2*i]} 
                onClick={() => {this.checkBox(2*i,false);}}
                className="testLabel" />
            <Checkbox key={year+" USNCO"}
                topic={year+" USNCO"} 
                checked={this.state.testChecked[2*i+1]} 
                onClick={() => {this.checkBox(2*i+1,false);}}
                className="testLabel"/>
            </div>;
        }

        return (
            <div className = "form">
            <form>
                <div className="formHeader">
                    <input type="checkbox" 
                            key="Topics"
                            checked={this.state.topicChecked.every((v)=>v)} 
                            onClick = {this.toggleTopics} 
                            onChange = {() => {}}/>
                    <label className="h3">Topics</label>
                </div>
                {topics}
                <div className="formHeader">
                    <input type="checkbox" 
                            key="Topics"
                            checked={this.state.testChecked.every((v)=>v)} 
                            onClick = {this.toggleTests} 
                            onChange = {() => {}}/>
                    <label className="h3">Tests</label>
                </div>
                {tests}
                <NavLink to='/quiz'  className = "button" onClick={this.submitForm}>Submit</NavLink>
            </form>
            </div>
        );
    }
}

export default Form;


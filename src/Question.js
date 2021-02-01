import React, {Component} from 'react'
import {getAnswer, getFormat} from './AnswerFormats'

class Question extends Component {
    constructor(props){
        super(props);
        const format = getFormat(this.props.data);
        const name = "format f"+ format;
        const questionArray={
        };
        this.state={
            classes: [name,name,name,name], 
            correct: ["","","",""],
            questions: questionArray, 
            hide:false,
            showsolution:false};
    }

    componentDidUpdate (PrevProps) {
        if (this.props.data!==PrevProps.data) {
            const format = getFormat(this.props.data);
            const name = "format f"+ format;
            this.setState({classes: [name,name,name,name], correct: ["","","",""]});
        }
    }

    hide = () => this.props.hide();
    toggleSolution = () => this.setState((state) => ({showsolution:!state.showsolution}));

    colorchange = (i) => {
        const answer = getAnswer(this.props.data);
        const correct = ["","","",""]
        if (i===answer) correct[i]=" correct";
        else correct[i]=" incorrect";
        this.setState({correct:correct});
    }


    render () {
        const question = this.props.data;
        const basename=process.env.PUBLIC_URL + "/"+question.year+question.test+"/q"+question.number;
        let test = "Locals";
        if (question.test==="N") test="USNCO";
        const problemName = (
            <div className = "problemName">
                {question.year +" "+ test + " Problem "+ question.number}
            </div>
            );
        const hasSolution = 2015<parseInt(question.year)&& parseInt(question.year)<2021 && question.test==="L";
        const solHint = this.state.correct.includes(" correct")&&hasSolution ? "solution" : " hide";
                return (
                <div>
                    <div className="flexBox">
                        {problemName}
                        <div className="close" onClick={this.hide}>
                            <div className="closetext">x</div>
                        </div>
                    </div>

                    <div className = "question">
                        <img className="othersize" src={basename+".png"} alt="Question"/>
                    </div>

                    <div className="answer">
                        <img className={this.state.classes[0]+this.state.correct[0]} src={basename+"-1.png"} onClick={() => this.colorchange(0)} alt="Answer A"/>
                        <img className={this.state.classes[1]+this.state.correct[1]} src={basename+"-2.png"} onClick={() => this.colorchange(1)} alt="Answer B"/>
                        <img className={this.state.classes[2]+this.state.correct[2]} src={basename+"-3.png"} onClick={() => this.colorchange(2)} alt="Answer C"/>
                        <img className={this.state.classes[3]+this.state.correct[3]} src={basename+"-4.png"} onClick={() => this.colorchange(3)} alt="Answer D"/>
                    </div>

                    <div className={solHint} onClick={this.toggleSolution}>Solution</div>
                    <img className={this.state.showsolution ? "othersize" : "hide"} src={process.env.PUBLIC_URL+"/"+question.year+question.test+" Solutions/q"+question.number+".png"} alt="Solution"/>
                </div>
                )
   
    }

}

export default Question;
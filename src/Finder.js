import React, { Component } from 'react'
import Question from './Question'

const Entry = (props) => {
    return (
    <React.Fragment>
        <label htmlFor={props.name}>{props.name}</label>
        <input
            type="text" 
            name={props.name} 
            value={props.state[props.name]} 
            onChange={props.handleChange}
            />
    </React.Fragment>
)}

export default class Finder extends Component {
    state = {
        questions:[],
        Year:"",
        Test:"",
        Number:"",
        dropButton:false,
    }

    hideQuestion = (index) => {
        const Qslice = this.state.questions.slice();
        this.setState({questions:Qslice.filter((v,i)=> i!==index)})
    }

    handleChange = (event) => {
        let {name,value} = event.target;
        this.setState({[name]: value});
    }
    handleClick = (test) => {
        this.setState({Test:test,dropButton:false});
    }
    showmenu = () => {
        this.setState({dropButton:true});
    }
    hidemenu = () => {
        this.setState({dropButton:false});
    }

    submitForm = () => {
        const {Year,Number,Test} = this.state;
        const question = {year: Year,number:Number,test:Test};
        this.setState({questions:[...this.state.questions,question],Test: "", Year: "", Number:""});
    }

    render() {
        let test;
        switch(this.state.Test){
            case "L": test="Locals"; break;
            case "N": test="USNCO"; break;
            default: test=""; 
        }
        return (
            <div>
            <div className = "margin20">
            {this.state.questions.map((q,i) => {return (
                <Question 
                    data={q} 
                    key={q.year+" "+q.test+" "+q.number} 
                    hide={() => this.hideQuestion(i)}
                />
            );})}
            </div>
            <div className = "form">
                <form>
                    <Entry 
                        name="Year" 
                        state={this.state} 
                        handleChange={this.handleChange}/> 
                    <label htmlFor={"Test"}>Test</label>
                    <div 
                        className="dropdown" 
                        onMouseLeave = {this.hidemenu}>
                    <input
                        type="text" 
                        name={"Test"} 
                        value={test}
                        readOnly={true}
                        onClick = {this.showmenu} 
                        />
                    {this.state.dropButton && (
                    <div className="content">
                        <div 
                            className = "dropButton" 
                            onClick={() => this.handleClick("L")}>Locals</div>
                        <div 
                            className = "dropButton" 
                            onClick={() => this.handleClick("N")}>USNCO</div>
                    </div>
                    )}
                    </div> 
                    <Entry 
                        name="Number" 
                        state={this.state} 
                        handleChange={this.handleChange}/> 
                    <input 
                        type="button" 
                        value="Submit" 
                        onClick={this.submitForm}/>
                </form>
            </div>
            </div>
        )
    }
}

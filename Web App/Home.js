import React, { Component } from 'react'
import Homebuttons from './Homebuttons'
import { NavLink } from 'react-router-dom'

// const Topic = (props) => {
//     return <NavLink to='/quiz'  className = {props.class} onClick = {() => {props.changeTopic(props.topic)}}>{props.text}</NavLink>
// }

const Topic = (props) => {
    return <NavLink to={'/quiz/'+props.topic}  className = {props.class}>{props.text}</NavLink>
}

export default class Home extends Component {

    changeTopic = (topic) => {
        this.props.changeTopic([topic],[]);
    }

    render() {
        return (
            <div className = "home">
                <div className = "header home">
                    USNCO Quizzes
                </div>

                <div className='container'>
                    <div className = "lightblue">
                        <Homebuttons />
                    </div>

                    <div className="flex between">
                        <p className = "topicheading">Topics:</p>
                        <NavLink to='/search'  className="topic fontsize2">Advanced Search</NavLink>
                    </div>

                    <div className = "topics">
                
                        <div className = "atopic">
                        <div className="topic">Q1-6</div>

                        {/* <Topic text = "Stoichiometry" class = "subtopic" changeTopic = {this.changeTopic} topic = {[0,0]}/>
                        <Topic text = "Unit Conversions" class = "subtopic hassub" changeTopic = {this.changeTopic} topic = {[0,1]}/>
                        <Topic text = " - Solutions" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[0,2]}/> */}
                        <NavLink to='/quiz/Stoichiometry' className="subtopic">Stoichiometry</NavLink>
                        <NavLink to='/quiz/Unit Conversions' className="subtopic hassub">Unit Conversions</NavLink>
                        <NavLink to='/quiz/Solutions' className="subsubtopic">- Solutions</NavLink>
                        </div>

                        <div className = "atopic">
                        <div className="topic">Q7-12</div>

                        {/* <Topic text = "Descriptive Chemistry" class = "subtopic" changeTopic = {this.changeTopic} topic = {[1,0]}/>
                        <Topic text = "Laboratory Practice" class = "subtopic" changeTopic = {this.changeTopic} topic = {[1,1]}/> */}
                        <NavLink to = "/quiz/Descriptive Chemistry" class = "subtopic">Descriptive Chemistry</NavLink>
                        <NavLink to = "/quiz/Laboratory Practice" class = "subtopic">Laboratory Practice</NavLink>
                        </div>

                        <div className = "atopic">
                        <div className="topic">Q13-18</div>

                        {/* <Topic text = "States of Matter" class = "subtopic hassub" changeTopic = {this.changeTopic} topic = {[2,0]}/>
                        <Topic text = "- Intermolecular Forces" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[2,1]}/>
                        <Topic text = "- Unit Cells" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[2,2]}/>
                        <Topic text = "- Kinetic Theory of Gases" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[2,3]}/> */}
                        <NavLink to = "/quiz/States of Matter" class = "subtopic hassub">States of Matter</NavLink>
                        <NavLink to = "/quiz/Intermolecular Forces" class = "subsubtopic">- Intermolecular Forces</NavLink>
                        <NavLink to = "/quiz/Unit Cells" class = "subsubtopic">- Unit Cells</NavLink>
                        <NavLink to = "/quiz/Kinetic Theory of Gases" class = "subsubtopic">- Kinetic Theory of Gases</NavLink>
                        
                        </div>

                        <div className = "atopic">
                        <div className="topic">Q19-24</div>

                        {/* <Topic text = "Thermodynamics" class = "subtopic hassub" changeTopic = {this.changeTopic} topic = {[3,0]}/>
                        <Topic text = "- Concepts" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[3,1]}/>
                        <Topic text = "- Calculations" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[3,2]}/> */}
                        <NavLink to = "/quiz/Thermodynamics" class = "subtopic hassub">Thermodynamics</NavLink>
                        <NavLink to = "/quiz/Thermodynamic Concepts" class = "subsubtopic">- Concepts</NavLink>
                        <NavLink to = "/quiz/Thermodynamic Calculations" class = "subsubtopic">- Calculations</NavLink>
                        
                        </div>

                        <div className = "atopic">
                        <div className="topic">Q25-30</div>

                        {/* <Topic text = "Kinetics" class = "subtopic hassub" changeTopic = {this.changeTopic} topic = {[4,0]}/>
                        <Topic text = "- Rate Laws" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[4,1]}/>
                        <Topic text = "- Arrhenius Equation" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[4,2]}/> */}
                        <NavLink to = "/quiz/Kinetics" class = "subtopic hassub">Kinetics</NavLink>
                        <NavLink to = "/quiz/Rate Laws" class = "subsubtopic">- Rate Laws</NavLink>
                        <NavLink to = "/quiz/Arrhenius Equation" class = "subsubtopic">- Arrhenius Equation</NavLink>
                        </div>

                        <div className = "atopic">
                        <div className="topic">Q31-36</div>

                        {/* <Topic text = "Equilibrium" class = "subtopic hassub" changeTopic = {this.changeTopic} topic = {[5,0]}/>
                        <Topic text = "- Titrations" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[5,1]}/> */}
                        <NavLink to = "/quiz/Equilibrium" class = "subtopic hassub">Equilibrium</NavLink>
                        <NavLink to = "/quiz/Titrations" class = "subsubtopic">- Titrations</NavLink>
                        </div>

                        <div className = "atopic">
                        <div className="topic">Q37-42</div>

                        {/* <Topic text = "Oxidation-Reduction" class = "subtopic hassub" changeTopic = {this.changeTopic} topic = {[6,0]}/>
                        <Topic text = "- Oxidation States" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[6,1]}/> */}
                        <NavLink to = "/quiz/Oxidation-Reduction" class = "subtopic hassub">Oxidation-Reduction</NavLink>
                        <NavLink to = "/quiz/Oxidation States" class = "subsubtopic">- Oxidation States</NavLink>
                        </div>

                        <div className = "atopic">
                        <div className="topic">Q43-48</div>

                        {/* <Topic text = "Atomic Structure" class = "subtopic hassub" changeTopic = {this.changeTopic} topic = {[7,0]}/>
                        <Topic text = "- Nuclear Chemistry" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[7,1]}/>
                        <Topic text = "- Electrons" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[7,2]}/>
                        <Topic text = "Periodicity" class = "subtopic" changeTopic = {this.changeTopic} topic = {[7,3]}/> */}
                        <NavLink to = "/quiz/Atomic Structure" class = "subtopic hassub">Atomic Structure</NavLink>
                        <NavLink to = "/quiz/Nuclear Chemistry" class = "subsubtopic">- Nuclear Chemistry</NavLink>
                        <NavLink to = "/quiz/Electrons" class = "subsubtopic">- Electrons</NavLink>
                        <NavLink to = "/quiz/Periodicity" class = "subtopic">Periodicity</NavLink>
                        </div>

                        <div className = "atopic">
                        <div className="topic">Q49-54</div>

                        {/* <Topic text = "Bonding" class = "subtopic hassub" changeTopic = {this.changeTopic} topic = {[8,0]}/>
                        <Topic text = "- Lewis Structures" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[8,1]}/>

                        <Topic text = "Molecular Structure" class = "subtopic hassub" changeTopic = {this.changeTopic} topic = {[8,2]}/>
                        <Topic text = "- VSEPR" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[8,3]}/>
                        <Topic text = "- Isomerization" class = "subsubtopic" changeTopic = {this.changeTopic} topic = {[8,4]}/> */}
                        <NavLink to = "/quiz/Bonding" class = "subtopic hassub">Bonding</NavLink>
                        <NavLink to = "/quiz/Lewis Structures" class = "subsubtopic">- Lewis Structures</NavLink>

                        <NavLink to = "/quiz/Molecular Structure" class = "subtopic hassub">Molecular Structure</NavLink>
                        <NavLink to = "/quiz/VSEPR" class = "subsubtopic">- VSEPR</NavLink>
                        <NavLink to = "/quiz/Isomerization" class = "subsubtopic">- Isomerization</NavLink>
                        </div>

                        <div className = "atopic">
                        <div className="topic">Q55-60</div>

                        {/* <Topic text = "Organic Chemistry" class = "subtopic" changeTopic = {this.changeTopic} topic = {[9,0]}/>
                        <Topic text = "Biochemistry" class = "subtopic" changeTopic = {this.changeTopic} topic = {[9,1]}/> */}
                        <NavLink to = "/quiz/Organic Chemistry" class = "subtopic hassub">Organic Chemistry</NavLink>
                        <NavLink to = "/quiz/Biochemistry" class = "subsubtopic">- Biochemistry</NavLink>
                        </div>
                    </div>

                    {/* <div className = "footer">
                            <NavLink to='/search'  className="rfloat topic fontsize2">Advanced Search</NavLink>
                        </div> */}
                </div>
            </div>
        )
    }
}

import React, { Component } from 'react'
import Homebuttons from './Homebuttons'
import {NavLink} from 'react-router-dom'

export default class About extends Component {
    render() {
        return (
            <div className = "home">
                <div className = "header home">
                    About
                </div>

                <div className="container">
                    <div className = "lightblue">
                        <Homebuttons />
                    </div>

                    <div className = "About">
                        <h1>USNCO-quizzes</h1>
                        <p>
                        The ACS has recently released an official organization of the USNCO by topic. However, at 60 questions and 1 hour each,
                        these tests are both time-consuming and broad. In my experience, this provides for a boring and stagnant-feeling study experience
                        akin to learning a musical piece by playing it all the way through.</p>

                        <p>It's much more effective to group similar problems together into one practice session.
                        This not only quickly reinforces the problem-solving skills and pattern recognition required for each section of the USNCO, 
                        but also makes studying more enjoyable, as students can see themselves improving at a particular topic in real time. </p>

                        <p>By reorganizing past exam problems by topic,
                        this site aims to streamline the studying process, providing a convenient (and hopefully enjoyable) USNCO practice experience.</p>
                    </div>

                    {/* <div className = "About">
                        <h1>Usage</h1>
                        <p>
                            Select a topic on the home screen to begin studying. Click "Advanced Search" for more detailed problem selection options.
                        </p>
                        <p>

                        </p>
                    </div> */}

                    <div className = "About">
                        <h1>Process</h1>
                        These problems were cropped as .png images from past USNCO exams with the help of a Java program utilizing Apache's PDFBox library,
                         assigned by hand to various subtopics, and then put into a React.js web app. Due to changes in organization,
                         problems from exams before 2015 may not correspond to the problem number ranges given in the homepage.
                        Where applicable, solutions were cropped in the same way from the ACS's official solutions. 
                    </div>

                
                </div>
            </div>
        )
    }
}

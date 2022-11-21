import React, { Component } from 'react'
import {NavLink} from 'react-router-dom'

export default class Homebuttons extends Component{
    render() {
        return (
<div className = "homebuttons">

    <div>
    <NavLink exact activeClassName="hide" to='/'  className = "navbutton width250">Home</NavLink>
    </div>

    <div>
    <NavLink exact activeClassName="hide" to='/about'  className = "navbutton width250">About</NavLink>
    </div>

    {/* <div>
    <NavLink exact activeClassName="hide" to='/quiz'  className = "navbutton">Practice??</NavLink>
    </div> */}

    {/* <div>
    <NavLink exact activeClassName="hide" to='/test'  className = "navbutton">Make Test</NavLink>
    </div> */}

    {/* <div>
    <NavLink exact activeClassName="hide" to='/search'  className = "navbutton">--nothing--</NavLink>
    </div> */}

    {/* <div>
    <NavLink exact activeClassName="hide" to='/links'  className = "navbutton width250">Links</NavLink>
    </div> */}

    <div>
    <a href='https://www.acs.org/content/acs/en/education/students/highschool/olympiad/pastexams.html'  className = "navbutton width250">To ACS</a>
    </div>
                    
</div>
        );
    }
}
import React, { Component } from 'react'

export default class Links extends Component {
    //maybe separate by topics and have a brief description of what each one does

    //anugrah, edward, chem.isodn.org, nmr, internet archive, symotter.org, chemguide.co.uk, chemlibretexts
    render() {
        return (
            <div>
                <div>
                <p className = "reviewheading">Inorganic</p>
                <a href="https://www.chemtube3d.com/">ChemTube3D</a>
                <p className="reviewsubheading">
                A helpful visualization website with animations and interactive 3D models of 
                unit cells and organic molecules and reactions
                </p>
                </div>
            </div>
        )
    }
}

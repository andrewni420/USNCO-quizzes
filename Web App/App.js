import React, {Component} from 'react'
import {Switch, Route} from 'react-router-dom'
import Quiz from './Quiz'
import Home from './Home'
import About from './About'
import Search from './Search'
import Links from './Links'
import {topicnames,topicnumbers} from './AnswerFormats'

const Topic = (props) => (<Route exact path={'/quiz/'+props.topicName} render={() => <Quiz topic = {props.topic} exclude={[]}/>}></Route>)


class App extends Component {

  state = {
    topic: -1
  }

  constructor(props){
    super(props);
    this.state.topic=[-1];
    this.state.exclude=[];
  }

  changeTopic = (topic,exclude) => {
      this.setState({topic:topic,exclude:exclude});
  }
  
  render() {

    const topics = topicnames.map((name,index)=>{
      let first=0;
      for (let i=0;i<topicnumbers.length;i++){
        if (topicnumbers[i]>index) break;
        first=i;
      }
      return <Route key={name} exact path={'/quiz/'+name} render={() => <Quiz topic = {[[first,index-topicnumbers[first]]]} exclude={[]}/>}></Route>
    })

    return (
      <div>
        <Switch>
        <Route exact path='/' render={(props) => <Home {...props} changeTopic={this.changeTopic} />}></Route>
        <Route exact path='/quiz' render={(props) => <Quiz {...props} topic = {this.state.topic} exclude={this.state.exclude}/>}></Route> 
        {topics}
        <Route exact path='/about' render={() => <About/>}></Route> 
        <Route path='/search' render={(props) => <Search {...props} changeTopic={this.changeTopic}/>}></Route> 
        <Route exact path='/links' render={() => <Links/>}></Route> 
      </Switch>
      </div>
    )
  }
}

export default App
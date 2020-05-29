/**
 * Created by grigo on 5/19/17.
 */
import React from  'react';
class UserForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {id:'', copilID: '', distanta:''};

      //  this.handleChange = this.handleChange.bind(this);
       // this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleUserChange=(event) =>{
        this.setState({copilID: event.target.value});
    }

    handleNameChange=(event) =>{
        this.setState({distanta: event.target.value});
    }

    handleSubmit =(event) =>{

        var user={id:1,
                copilID:this.state.copilID,
                distanta:this.state.distanta
        }
        console.log('A user was submitted: ');
        console.log(user);
        this.props.addFunc(user);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    ID Copil:
                    <input type="text" value={this.state.copilID} onChange={this.handleUserChange} />
                </label><br/>
                <label>
                    Distanta:
                    <input type="text" value={this.state.distanta} onChange={this.handleNameChange} />
                </label><br/><br/>

                <input type="submit" value="Submit" />
            </form>
        );
    }
}
export default UserForm;
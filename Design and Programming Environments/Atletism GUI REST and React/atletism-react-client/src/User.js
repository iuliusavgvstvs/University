
import React from  'react';
import './UserApp.css'

class UserRow extends React.Component{

    handleClicke=(event)=>{
        console.log('delete button pentru '+this.props.user.id);
        this.props.deleteFunc(this.props.user.id);
    }

    render() {
        return (
            <tr>
                <td>{this.props.user.id}</td>
                <td>{this.props.user.copilID}</td>
                <td>{this.props.user.distanta}</td>
                <td><button  onClick={this.handleClicke}>Delete</button></td>
            </tr>
        );
    }
}
/*<form onSubmit={this.handleClicke}><input type="submit" value="Delete"/></form>*/
/**/
class UserTable extends React.Component {
    render() {
        var rows = [];
        var functieStergere=this.props.deleteFunc;
        this.props.users.forEach(function(user) {

            rows.push(<UserRow user={user} key={user.id} deleteFunc={functieStergere} />);
        });
        return (<div className="UserTable">

            <table className="center">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>CopilID</th>
                    <th>Distanta</th>

                    <th></th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>

            </div>
        );
    }
}

export default UserTable;
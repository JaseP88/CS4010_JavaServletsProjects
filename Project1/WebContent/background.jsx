
var App = React.createClass ({
	render: function() {
		return (
		<form id="myForm" method="POST" action="ReadURLServlet" >
			<div id="buttonDiv">
				<div id='background'>
					<UrlButton />
				</div>
				
					<SubmitButton />
				
			</div></form>
		);	
	}
});




var TextBox = React.createClass ({
	render: function() {
		return <div><input type="text" id="textBox" name="urlname" /></div>;
	}

});

var UrlButton = React.createClass ({
	getInitialState: function() {
	    return {textboxes: []};

	},

	renderTextbox: function(e) {
		e.preventDefault();
		var txt = this.state.textboxes;
		txt.push(<TextBox />);
		this.setState({textboxes: txt});
	},

	render: function() {
		return <div><button id="urlButton" onClick={this.renderTextbox}>Add URL</button>{this.state.textboxes}</div>;
	}

});



var SubmitButton = React.createClass ({
	render: function() {
		return <input type="submit" id="submitButton" />;
	}
});



ReactDOM.render(<App />, document.getElementById('app'));




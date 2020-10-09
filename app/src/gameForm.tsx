import * as React from "react";
import Stomp from "stompjs";

interface IGameFormProps {
  stompClient: Stomp.Client;
}

class GameForm extends React.PureComponent<IGameFormProps> {
  private isJoinForm: boolean;

  render = () => {
    return (
      <div id="gameFormContainer">
        <nav className="navbar navbar-light bg-light">
          <form className="form-inline">
            <button
              className={`btn ${
                this.isJoinForm ? "btn-outline-primary" : "btn-primary"
              }`}
              type="button"
            >
              Join game
            </button>
            <button
              className={`btn ${
                this.isJoinForm ? "btn-primary" : "btn-outline-primary"
              }`}
              type="button"
            >
              Create game
            </button>
          </form>
        </nav>
        <form
          id="userJoinForm"
          name="userJoinForm"
          className="w-20 mx-auto" /*onSubmit={this.connect}*/
        >
          <div className="form-group">
            <label htmlFor="name">Name :</label>
            <input
              type="text"
              className="form-control"
              id="name"
              aria-describedby="name"
              placeholder="Enter name"
            />
          </div>
          {this.isJoinForm && (
            <div className="form-group">
              <label htmlFor="room">Game ID :</label>
              <input
                type="text"
                className="form-control"
                id="room"
                aria-describedby="exampleInputRoom"
                placeholder="Enter game ID"
              />
            </div>
          )}
          <button type="submit" className="btn btn-primary">
            Submit
          </button>
        </form>
      </div>
    );
  };
}

export default GameForm;

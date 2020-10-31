import * as React from "react";
import $ from "jquery";
import { stompClient } from "../services/stompClient";
import { ICreateMessagePayload } from "../models";

interface IGameFormProps {
  joinFormError: string;
}

class GameForm extends React.Component<IGameFormProps> {
  state = {
    isJoinForm: true,
  };

  render = () => {
    return (
      <div id="gameFormContainer">
        <nav className="navbar navbar-light bg-light">
          <form className="form-inline">
            <button
              className={`btn ${this.state.isJoinForm ? "btn-primary" : "btn-outline-primary"
                }`}
              type="button"
              onClick={() => {
                this.setState({
                  isJoinForm: true,
                });
              }}
            >
              Rejoindre une partie
            </button>
            <button
              className={`btn ${this.state.isJoinForm ? "btn-outline-primary" : "btn-primary"
                }`}
              type="button"
              onClick={() => {
                this.setState({
                  isJoinForm: false,
                });
              }}
            >
              Créer une partie
            </button>
          </form>
        </nav>
        <form id="gameForm" name="gameForm" onSubmit={this.submitForm}>
          <div className="form-group">
            <label htmlFor="name">Nom :</label>
            <input
              required
              type="text"
              className="form-control"
              id="name"
              placeholder="Votre nom"
            />
          </div>
          {this.state.isJoinForm ? (
            <div className="form-group">
              <label htmlFor="gameId">ID de la partie:</label>
              <input
                required
                type="text"
                className="form-control"
                id="gameId"
                placeholder="XXXXXX"
              />
            </div>
          ) : (
              <div id="createGameForm">
                <div className="form-group">
                  <label htmlFor="numOfPlayers">Nombre de joueurs :</label>
                  <input
                    type="number"
                    className="form-control"
                    id="numOfPlayers"
                    min="2"
                    max="6"
                    defaultValue="2"
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="numOfTeams">Nombre d'équipes :</label>
                  <input
                    type="number"
                    className="form-control"
                    id="numOfTeams"
                    min="2"
                    max="3"
                    defaultValue="2"
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="numOfRounds">Nombre de manches :</label>
                  <input
                    type="number"
                    className="form-control"
                    id="numOfRounds"
                    min="3"
                    max="9"
                    step="3"
                    defaultValue="3"
                  />
                </div>
              </div>
            )}
          <div
            className={
              !this.props.joinFormError
                ? "error-tooltip d-none"
                : "error-tooltip border border-danger rounded"
            }
          >
            {this.props.joinFormError}
          </div>
          <button type="submit" className="btn btn-primary">
            Valider
          </button>
        </form>
      </div>
    );
  };

  private submitForm = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    let name = $("#name").val();
    if (this.state.isJoinForm) {
      let gameId = $("#gameId").val().toString();
      stompClient.send(`/app/join/${gameId}/${name}`, {});
    } else {
      let numOfPlayers = $("#numOfPlayers").val();
      let numOfTeams = $("#numOfTeams").val();
      let numOfRounds = $("#numOfRounds").val();
      const payload: ICreateMessagePayload = {
        name: name.toString(),
        numOfPlayers: parseInt(numOfPlayers.toString()),
        numOfTeams: parseInt(numOfTeams.toString()),
        numOfRounds: parseInt(numOfRounds.toString()),
      };
      stompClient.send(`/app/create`, {}, JSON.stringify(payload));
    }
  };
}

export default GameForm;

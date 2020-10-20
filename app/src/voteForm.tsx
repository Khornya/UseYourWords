import React from "react";
import { stompClient } from "./stompClient";
import $ from "jquery";

interface IVoteForm {
    gameId: string
    answers: string[]
}

export class VoteForm extends React.Component<IVoteForm> {
    state = {
        showSubmitButton: true
    }

    render = () => {
        return (
            <div className="voteForm">
                <div className="form-check">
                    <form
                        id="voteForm"
                        name="voteForm"
                        className="w-20 mx-auto"
                        onSubmit={this.submitForm}
                    >
                        {this.props.answers.map((answer, index) => {
                            return (
                                <div key={index}>
                                    <input className="form-check-input" type="radio" name="answer" id={index.toString()} value={index} required />
                                    <label className="form-check-label" htmlFor="answer">
                                        {answer}
                                    </label>
                                </div>
                            )
                        })}
                        {this.state.showSubmitButton ?
                            <button type="submit" className="btn btn-primary">
                                Vote
                            </button>
                        :
                            <div className="waitingMessage">Please wait for the other players ...</div>}
                    </form>
                </div>
            </div>
        )
    }

    private submitForm = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        this.setState({
            showSubmitButton: false
        })
        let answerIndex = $("input[name=answer]:checked").val()
        stompClient.send(`/app/vote/${this.props.gameId}/${answerIndex}`, {}, "");
    }
}
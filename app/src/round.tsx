import React from "react";
import { IAnswerMessagePayload, IGameState } from "./models"
import { Timer } from "./timer";
import { stompClient } from "./stompClient"
import $ from "jquery";
import { VoteForm } from "./voteForm";

interface IRoundProps {
    gameId: string
    playerIndex: number
    playerAnswerIndex: number
    gameState: IGameState
}

export class Round extends React.Component<IRoundProps> {
    state = {
        showSubmitButton: true
    }

    componentWillReceiveProps = () => {
        this.setState({
            showSubmitButton: true
        })
    }

    render = () => {
        const textParts = this.props.gameState.element.toFillText?.split("[...]")
        let input = (index: number) => {
            return <input required key={index} type="text" className="form-control" name="response" placeholder="..." />
        }
        return (
            <div className="round">

                <h3>Round {this.props.gameState.roundNumber}</h3>
                <form
                    id="roundForm"
                    name="roundForm"
                    onSubmit={this.submitForm}
                >
                    {{
                        "PHOTO": <div className="form-group">
                            <img className="element photo" src={this.props.gameState.element.url} alt={this.props.gameState.element.name} />
                            {this.props.gameState.displayAnswerForm && <input required type="text" className="form-control" name="response" placeholder="Type a funny subtitle here" />}
                        </div>,
                        "VIDEO": <div className="form-group">
                            <video className="element"  src={this.props.gameState.element.url} muted controls />
                            {this.props.gameState.displayAnswerForm && <input required type="text" className="form-control" name="response" placeholder="Type a funny subtitle here" />}
                        </div>,
                        "TEXT": <div className="form-group">
                            <div className="element text">
                                {this.props.gameState.displayAnswerForm ? textParts?.map((textPart, index) => {
                                    if (textPart === "") {
                                        return input(index)
                                    } else {
                                        return <p key={index}>{textPart}</p>
                                    }
                                    // } else {
                                    //     return (
                                    //         <span className="textPartGroup"key={index}>
                                    //             <p>{textPart}</p>
                                    //             {input(index)}
                                    //         </span>)
                                    // }
                                }) : <p>{this.props.gameState.element.url}</p>}
                            </div>
                        </div>
                    }[this.props.gameState.element.type]}
                    {this.props.gameState.displayAnswerForm && this.state.showSubmitButton &&
                        <div className="submitWithTimer">
                            <button type="submit" className={`btn ${this.props.gameState.showTimer ? "btn-danger" : "btn-primary"}`}>
                                Answer
                            </button>
                            {this.props.gameState.showTimer && <Timer duration={10} />}
                        </div>
                    }
                    {this.props.gameState.displayAnswerForm && !this.state.showSubmitButton &&
                        <div className="waitingMessage alert alert-primary" role="alert">Please wait for the other players ...</div>
                    }
                </form>
                {this.props.gameState.displayVoteForm && <VoteForm gameId={this.props.gameId} answers={this.props.gameState.answers} playerAnswerIndex={this.props.playerAnswerIndex} />}
            </div>
        )
    }

    private submitForm = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        this.setState({ showSubmitButton: false })
        let input = $("input[name=response]").map(function () { return $(this).val().toString(); }).get();
        const payload: IAnswerMessagePayload = {
            type: this.props.gameState.element.type,
            answers: input,
            playerIndex: this.props.playerIndex
        };
        stompClient.send(`/app/answer/${this.props.gameId}`, {}, JSON.stringify(payload));
    }
}
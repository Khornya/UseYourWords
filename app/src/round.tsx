import React from "react";
import { Element, IAnswerMessagePayload } from "./models"
import { Timer } from "./timer";
import { stompClient } from "./stompClient"
import $ from "jquery";

interface IRoundProps {
    gameId: string
    playerIndex: number
    roundNumber: number
    element: Element
    showTimer: boolean
    displayAnswerForm: boolean
}

export class Round extends React.Component<IRoundProps> {
    state = {
        showSubmitButton: true
    }

    render = () => {
        const textParts = this.props.element.url.split("[...]")
        let input = (index: number) => {
            return <input required key={index} type="text" className="form-control" name="response" placeholder="Type something funny here" />
        }
        return (
            <div className="round">
                <h2>Round n°{this.props.roundNumber}</h2>
                {this.props.showTimer && <Timer duration={10} />}

                {this.props.displayAnswerForm && <form
                    id="roundForm"
                    name="roundForm"
                    className="w-20 mx-auto"
                    onSubmit={this.submitForm}
                >
                    {{
                        "PHOTO": <div className="form-group">
                            <img src={this.props.element.url} alt={this.props.element.name} />
                            <input required type="text" className="form-control" name="response" placeholder="Type a funny subtitle here" />
                        </div>,
                        "VIDEO": <div className="form-group">
                            <iframe title="videoFrame" width="560" height="315" src={this.props.element.url} frameBorder="0" allowFullScreen></iframe>
                            <input required type="text" className="form-control" name="response" placeholder="Type a funny subtitle here" />
                        </div>,
                        "TEXT": <div className="form-group">
                            <div className="element">
                                {textParts.map((textPart, index) => {
                                    if (textPart === "") {
                                        return input(index)
                                    } else if (index === textParts.length - 1) {
                                        return <p key={index}>{textPart}</p>
                                    } else {
                                        return (
                                            <span key={index}>
                                                <p>{textPart}</p>
                                                {input(index)}
                                            </span>)
                                    }
                                })}
                            </div>
                        </div>
                    }[this.props.element.type]}
                    {this.state.showSubmitButton ? <button type="submit" className="btn btn-primary">
                        Answer
                    </button> : <div className="waitingMessage">Please wait for the other players ...</div>}
                </form> }
            </div>
        )
    }

    private submitForm = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        this.setState({ showSubmitButton: false })
        let input = $("input[name=response]").map(function(){return $(this).val().toString();}).get();
        const payload: IAnswerMessagePayload = {
            type: this.props.element.type,
            answers: input,
            playerIndex: this.props.playerIndex
        };
        stompClient.send(`/app/answer/${this.props.gameId}`, {}, JSON.stringify(payload));
    }
}
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

    componentWillReceiveProps = () => {
        this.setState({
            showSubmitButton: true
        })
    }

    render = () => {
        const textParts = this.props.element.url.split("[...]")
        let input = (index: number) => {
            return <input required key={index} type="text" className="form-control" name="response" placeholder="Type something funny here" />
        }
        return (
            <div className="round">

                <h3>Round {this.props.roundNumber}</h3>
                <form
                    id="roundForm"
                    name="roundForm"
                    onSubmit={this.submitForm}
                >
                    {{
                        "PHOTO": <div className="form-group">
                            <img className="element" src={this.props.element.url} alt={this.props.element.name} />
                            {this.props.displayAnswerForm && <input required type="text" className="form-control" name="response" placeholder="Type a funny subtitle here" />}
                        </div>,
                        "VIDEO": <div className="form-group">
                            <iframe className="element" title="videoFrame" width="560" height="315" src={this.props.element.url} frameBorder="0" allowFullScreen></iframe>
                            {this.props.displayAnswerForm && <input required type="text" className="form-control" name="response" placeholder="Type a funny subtitle here" />}
                        </div>,
                        "TEXT": <div className="form-group">
                            <div className="element">
                                {this.props.displayAnswerForm ? textParts.map((textPart, index) => {
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
                                }) : <p>{this.props.element.url}</p>}
                            </div>
                        </div>
                    }[this.props.element.type]}
                    {this.props.displayAnswerForm && this.state.showSubmitButton &&
                        <div className="submitWithTimer">
                            <button type="submit" className="btn btn-primary">
                                Answer
                            </button>
                            {this.props.showTimer && <Timer duration={10} />}
                        </div>
                    }
                    {this.props.displayAnswerForm && !this.state.showSubmitButton &&
                        <div className="waitingMessage alert alert-primary" role="alert">Please wait for the other players ...</div>
                    }    
                </form>
            </div>
        )
    }

    private submitForm = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        this.setState({ showSubmitButton: false })
        let input = $("input[name=response]").map(function () { return $(this).val().toString(); }).get();
        const payload: IAnswerMessagePayload = {
            type: this.props.element.type,
            answers: input,
            playerIndex: this.props.playerIndex
        };
        stompClient.send(`/app/answer/${this.props.gameId}`, {}, JSON.stringify(payload));
    }
}
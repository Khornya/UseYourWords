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
}

export class Round extends React.Component<IRoundProps> {
    render = () => {
        const textParts = this.props.element.url.split("[...]")
        let input = (index: number) => {
            return <input required key={index} type="text" className="form-control" name="response" placeholder="..." />
        }
        return (
            <div className="round">
                <h2>Round n°{this.props.roundNumber}</h2>
                {this.props.showTimer && <Timer duration={10} />}
                <form
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
                            <video src={this.props.element.url} />
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
                                                {input}
                                            </span>)
                                    }
                                })}
                            </div>
                        </div>
                    }[this.props.element.type]}
                    <button type="submit" className="btn btn-primary">
                        Answer
                    </button>
                </form>
            </div>
        )
    }

    private submitForm = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        let input = $("input[name=response]").val()
        const payload: IAnswerMessagePayload = {
            type: this.props.element.type,
            answers: Array.isArray(input) ? input : [input as string],
            playerIndex: this.props.playerIndex
        };
        stompClient.send(`/app/answer/${this.props.gameId}`, {}, JSON.stringify(payload));
    }
}
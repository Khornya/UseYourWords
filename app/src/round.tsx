import React from "react";
import { Element } from "./models"

interface IRoundProps {
    roundNumber: number
    element: Element
}

export class Round extends React.Component<IRoundProps> {
    render = () => {
        console.log("elementType", this.props.element.type)
        const textParts = this.props.element.url.split("[...]")
        const input = <input required type="text" className="form-control" name="response" placeholder="..." />
        return (
            <div className="round">
                <h2>Round n°{this.props.roundNumber}</h2>
                <form
                    id="roundForm"
                    name="roundForm"
                    className="w-20 mx-auto"
                // onSubmit={this.submitForm}
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
                                        return input
                                    } else if (index === textParts.length - 1) {
                                        return <p>{textPart}</p>
                                    } else {
                                        return (
                                            <span>
                                                <p>{textPart}</p>
                                                {input}
                                            </span>)
                                    }
                                })}
                            </div>
                        </div>
                    }[this.props.element.type]}
                </form>
            </div>
        )
    }
}
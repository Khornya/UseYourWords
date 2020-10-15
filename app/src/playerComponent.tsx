import React from "react"
import { Player } from "./models";

interface IPlayerProps {
    player: Player
}

export class PlayerComponent extends React.Component<IPlayerProps> {
    render = () => {
        return (
            <li className="player">
                {this.props.player.name}
            </li>
        )
    }
}
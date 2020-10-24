import React from "react"

interface ITimerProps {
    duration: number
}

export class Timer extends React.Component<ITimerProps> {
    state = {
        remainingTime: this.props.duration
    }

    componentDidMount = () => {
        const timer = setInterval(() => {
            if (this.state.remainingTime > 1) {
                this.setState({
                    remainingTime: this.state.remainingTime -1
                })
            } else {
                clearInterval(timer)
            }
        }, 1000)
    }

    render = () => {
        return (
            <div className="timer">
                {this.state.remainingTime}
            </div>
        )
    }
}
import * as React from "react";

class WaitingMessage extends React.PureComponent {
  render = () => {
    return (
      <div className="waitingMessage d-flex justify-content-center">
        <div className="spinner-border text-primary" role="status">
          <span className="sr-only"></span>
        </div>
        <div className="message">Please wait...</div>
      </div>
    );
  };
}

export default WaitingMessage;

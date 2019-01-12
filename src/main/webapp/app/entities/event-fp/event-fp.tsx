import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './event-fp.reducer';
import { IEventFp } from 'app/shared/model/event-fp.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventFpProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class EventFp extends React.Component<IEventFpProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { eventList, match } = this.props;
    return (
      <div>
        <h2 id="event-fp-heading">
          Events
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Event
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Fee</th>
                <th>Number Of Members</th>
                <th>Type Of Participation</th>
                <th>Free Event</th>
                <th>Abstract Select</th>
                <th>Test</th>
                <th>Status</th>
                <th>Parent Event</th>
                <th>Event Type</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {eventList.map((event, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${event.id}`} color="link" size="sm">
                      {event.id}
                    </Button>
                  </td>
                  <td>{event.name}</td>
                  <td>{event.fee}</td>
                  <td>{event.numberOfMembers}</td>
                  <td>{event.typeOfParticipation}</td>
                  <td>{event.freeEvent ? 'true' : 'false'}</td>
                  <td>{event.abstractSelect ? 'true' : 'false'}</td>
                  <td>{event.test ? 'true' : 'false'}</td>
                  <td>{event.status}</td>
                  <td>{event.parentEvent}</td>
                  <td>{event.eventType}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${event.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${event.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${event.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ event }: IRootState) => ({
  eventList: event.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EventFp);

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './event-fp.reducer';
import { IEventFp } from 'app/shared/model/event-fp.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEventFpDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EventFpDetail extends React.Component<IEventFpDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { eventEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Event [<b>{eventEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{eventEntity.name}</dd>
            <dt>
              <span id="fee">Fee</span>
            </dt>
            <dd>{eventEntity.fee}</dd>
            <dt>
              <span id="numberOfMembers">Number Of Members</span>
            </dt>
            <dd>{eventEntity.numberOfMembers}</dd>
            <dt>
              <span id="typeOfParticipation">Type Of Participation</span>
            </dt>
            <dd>{eventEntity.typeOfParticipation}</dd>
            <dt>
              <span id="freeEvent">Free Event</span>
            </dt>
            <dd>{eventEntity.freeEvent ? 'true' : 'false'}</dd>
            <dt>
              <span id="abstractSelect">Abstract Select</span>
            </dt>
            <dd>{eventEntity.abstractSelect ? 'true' : 'false'}</dd>
            <dt>
              <span id="test">Test</span>
            </dt>
            <dd>{eventEntity.test ? 'true' : 'false'}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{eventEntity.status}</dd>
            <dt>
              <span id="parentEvent">Parent Event</span>
            </dt>
            <dd>{eventEntity.parentEvent}</dd>
            <dt>
              <span id="eventType">Event Type</span>
            </dt>
            <dd>{eventEntity.eventType}</dd>
          </dl>
          <Button tag={Link} to="/entity/event-fp" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/event-fp/${eventEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ event }: IRootState) => ({
  eventEntity: event.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EventFpDetail);

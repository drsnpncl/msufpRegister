import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './event-fp.reducer';
import { IEventFp } from 'app/shared/model/event-fp.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEventFpUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEventFpUpdateState {
  isNew: boolean;
}

export class EventFpUpdate extends React.Component<IEventFpUpdateProps, IEventFpUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { eventEntity } = this.props;
      const entity = {
        ...eventEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/event-fp');
  };

  render() {
    const { eventEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="msufpRegisterApp.event.home.createOrEditLabel">Create or edit a Event</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : eventEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="event-fp-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="event-fp-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="feeLabel" for="fee">
                    Fee
                  </Label>
                  <AvField id="event-fp-fee" type="string" className="form-control" name="fee" />
                </AvGroup>
                <AvGroup>
                  <Label id="numberOfMembersLabel" for="numberOfMembers">
                    Number Of Members
                  </Label>
                  <AvField id="event-fp-numberOfMembers" type="text" name="numberOfMembers" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeOfParticipationLabel" for="typeOfParticipation">
                    Type Of Participation
                  </Label>
                  <AvField id="event-fp-typeOfParticipation" type="text" name="typeOfParticipation" />
                </AvGroup>
                <AvGroup>
                  <Label id="freeEventLabel" check>
                    <AvInput id="event-fp-freeEvent" type="checkbox" className="form-control" name="freeEvent" />
                    Free Event
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="abstractSelectLabel" check>
                    <AvInput id="event-fp-abstractSelect" type="checkbox" className="form-control" name="abstractSelect" />
                    Abstract Select
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="testLabel" check>
                    <AvInput id="event-fp-test" type="checkbox" className="form-control" name="test" />
                    Test
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="status">
                    Status
                  </Label>
                  <AvField id="event-fp-status" type="text" name="status" />
                </AvGroup>
                <AvGroup>
                  <Label id="parentEventLabel" for="parentEvent">
                    Parent Event
                  </Label>
                  <AvField id="event-fp-parentEvent" type="text" name="parentEvent" />
                </AvGroup>
                <AvGroup>
                  <Label id="eventTypeLabel" for="eventType">
                    Event Type
                  </Label>
                  <AvField id="event-fp-eventType" type="text" name="eventType" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/event-fp" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  eventEntity: storeState.event.entity,
  loading: storeState.event.loading,
  updating: storeState.event.updating,
  updateSuccess: storeState.event.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EventFpUpdate);

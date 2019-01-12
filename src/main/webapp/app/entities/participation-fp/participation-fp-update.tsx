import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUserGroupFp } from 'app/shared/model/user-group-fp.model';
import { getEntities as getUserGroups } from 'app/entities/user-group-fp/user-group-fp.reducer';
import { IEventFp } from 'app/shared/model/event-fp.model';
import { getEntities as getEvents } from 'app/entities/event-fp/event-fp.reducer';
import { getEntity, updateEntity, createEntity, reset } from './participation-fp.reducer';
import { IParticipationFp } from 'app/shared/model/participation-fp.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IParticipationFpUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IParticipationFpUpdateState {
  isNew: boolean;
  userGroupId: string;
  eventId: string;
}

export class ParticipationFpUpdate extends React.Component<IParticipationFpUpdateProps, IParticipationFpUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userGroupId: '0',
      eventId: '0',
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

    this.props.getUserGroups();
    this.props.getEvents();
  }

  saveEntity = (event, errors, values) => {
    values.regDate = new Date(values.regDate);

    if (errors.length === 0) {
      const { participationEntity } = this.props;
      const entity = {
        ...participationEntity,
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
    this.props.history.push('/entity/participation-fp');
  };

  render() {
    const { participationEntity, userGroups, events, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="msufpRegisterApp.participation.home.createOrEditLabel">Create or edit a Participation</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : participationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="participation-fp-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="regDateLabel" for="regDate">
                    Reg Date
                  </Label>
                  <AvInput
                    id="participation-fp-regDate"
                    type="datetime-local"
                    className="form-control"
                    name="regDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.participationEntity.regDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="paymentModeLabel" for="paymentMode">
                    Payment Mode
                  </Label>
                  <AvField id="participation-fp-paymentMode" type="text" name="paymentMode" />
                </AvGroup>
                <AvGroup>
                  <Label id="paymentAcceptLabel" check>
                    <AvInput id="participation-fp-paymentAccept" type="checkbox" className="form-control" name="paymentAccept" />
                    Payment Accept
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="abstractSelectLabel" check>
                    <AvInput id="participation-fp-abstractSelect" type="checkbox" className="form-control" name="abstractSelect" />
                    Abstract Select
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="userGroup.id">User Group</Label>
                  <AvInput id="participation-fp-userGroup" type="select" className="form-control" name="userGroupId">
                    <option value="" key="0" />
                    {userGroups
                      ? userGroups.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="event.id">Event</Label>
                  <AvInput id="participation-fp-event" type="select" className="form-control" name="eventId">
                    <option value="" key="0" />
                    {events
                      ? events.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/participation-fp" replace color="info">
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
  userGroups: storeState.userGroup.entities,
  events: storeState.event.entities,
  participationEntity: storeState.participation.entity,
  loading: storeState.participation.loading,
  updating: storeState.participation.updating,
  updateSuccess: storeState.participation.updateSuccess
});

const mapDispatchToProps = {
  getUserGroups,
  getEvents,
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
)(ParticipationFpUpdate);

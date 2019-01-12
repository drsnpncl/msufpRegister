import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './participation-fp.reducer';
import { IParticipationFp } from 'app/shared/model/participation-fp.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParticipationFpDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParticipationFpDetail extends React.Component<IParticipationFpDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { participationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Participation [<b>{participationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="regDate">Reg Date</span>
            </dt>
            <dd>
              <TextFormat value={participationEntity.regDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="paymentMode">Payment Mode</span>
            </dt>
            <dd>{participationEntity.paymentMode}</dd>
            <dt>
              <span id="paymentAccept">Payment Accept</span>
            </dt>
            <dd>{participationEntity.paymentAccept ? 'true' : 'false'}</dd>
            <dt>
              <span id="abstractSelect">Abstract Select</span>
            </dt>
            <dd>{participationEntity.abstractSelect ? 'true' : 'false'}</dd>
            <dt>User Group</dt>
            <dd>{participationEntity.userGroupId ? participationEntity.userGroupId : ''}</dd>
            <dt>Event</dt>
            <dd>{participationEntity.eventId ? participationEntity.eventId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/participation-fp" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/participation-fp/${participationEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ participation }: IRootState) => ({
  participationEntity: participation.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParticipationFpDetail);

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './participation-fp.reducer';
import { IParticipationFp } from 'app/shared/model/participation-fp.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParticipationFpProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ParticipationFp extends React.Component<IParticipationFpProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { participationList, match } = this.props;
    return (
      <div>
        <h2 id="participation-fp-heading">
          Participations
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Participation
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Reg Date</th>
                <th>Payment Mode</th>
                <th>Payment Accept</th>
                <th>Abstract Select</th>
                <th>User Group</th>
                <th>Event</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {participationList.map((participation, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${participation.id}`} color="link" size="sm">
                      {participation.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={participation.regDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{participation.paymentMode}</td>
                  <td>{participation.paymentAccept ? 'true' : 'false'}</td>
                  <td>{participation.abstractSelect ? 'true' : 'false'}</td>
                  <td>
                    {participation.userGroupId ? (
                      <Link to={`user-group-fp/${participation.userGroupId}`}>{participation.userGroupId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{participation.eventId ? <Link to={`event-fp/${participation.eventId}`}>{participation.eventId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${participation.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${participation.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${participation.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ participation }: IRootState) => ({
  participationList: participation.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParticipationFp);

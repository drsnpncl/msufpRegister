import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-group-fp.reducer';
import { IUserGroupFp } from 'app/shared/model/user-group-fp.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserGroupFpDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserGroupFpDetail extends React.Component<IUserGroupFpDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userGroupEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            UserGroup [<b>{userGroupEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="groupName">Group Name</span>
            </dt>
            <dd>{userGroupEntity.groupName}</dd>
            <dt>Users</dt>
            <dd>
              {userGroupEntity.users
                ? userGroupEntity.users.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === userGroupEntity.users.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}{' '}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/user-group-fp" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/user-group-fp/${userGroupEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ userGroup }: IRootState) => ({
  userGroupEntity: userGroup.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserGroupFpDetail);

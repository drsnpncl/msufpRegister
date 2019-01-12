import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserGroupFp from './user-group-fp';
import UserGroupFpDetail from './user-group-fp-detail';
import UserGroupFpUpdate from './user-group-fp-update';
import UserGroupFpDeleteDialog from './user-group-fp-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserGroupFpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserGroupFpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserGroupFpDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserGroupFp} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserGroupFpDeleteDialog} />
  </>
);

export default Routes;

import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EventFp from './event-fp';
import EventFpDetail from './event-fp-detail';
import EventFpUpdate from './event-fp-update';
import EventFpDeleteDialog from './event-fp-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EventFpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EventFpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EventFpDetail} />
      <ErrorBoundaryRoute path={match.url} component={EventFp} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EventFpDeleteDialog} />
  </>
);

export default Routes;

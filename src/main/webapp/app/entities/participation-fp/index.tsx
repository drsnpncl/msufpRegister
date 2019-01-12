import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParticipationFp from './participation-fp';
import ParticipationFpDetail from './participation-fp-detail';
import ParticipationFpUpdate from './participation-fp-update';
import ParticipationFpDeleteDialog from './participation-fp-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParticipationFpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParticipationFpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParticipationFpDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParticipationFp} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ParticipationFpDeleteDialog} />
  </>
);

export default Routes;

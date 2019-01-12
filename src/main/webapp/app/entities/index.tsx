import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EventFp from './event-fp';
import UserGroupFp from './user-group-fp';
import ParticipationFp from './participation-fp';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/event-fp`} component={EventFp} />
      <ErrorBoundaryRoute path={`${match.url}/user-group-fp`} component={UserGroupFp} />
      <ErrorBoundaryRoute path={`${match.url}/participation-fp`} component={ParticipationFp} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;

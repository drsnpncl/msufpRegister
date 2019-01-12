import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParticipationFp, defaultValue } from 'app/shared/model/participation-fp.model';

export const ACTION_TYPES = {
  FETCH_PARTICIPATION_LIST: 'participation/FETCH_PARTICIPATION_LIST',
  FETCH_PARTICIPATION: 'participation/FETCH_PARTICIPATION',
  CREATE_PARTICIPATION: 'participation/CREATE_PARTICIPATION',
  UPDATE_PARTICIPATION: 'participation/UPDATE_PARTICIPATION',
  DELETE_PARTICIPATION: 'participation/DELETE_PARTICIPATION',
  RESET: 'participation/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParticipationFp>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ParticipationFpState = Readonly<typeof initialState>;

// Reducer

export default (state: ParticipationFpState = initialState, action): ParticipationFpState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PARTICIPATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARTICIPATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARTICIPATION):
    case REQUEST(ACTION_TYPES.UPDATE_PARTICIPATION):
    case REQUEST(ACTION_TYPES.DELETE_PARTICIPATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PARTICIPATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARTICIPATION):
    case FAILURE(ACTION_TYPES.CREATE_PARTICIPATION):
    case FAILURE(ACTION_TYPES.UPDATE_PARTICIPATION):
    case FAILURE(ACTION_TYPES.DELETE_PARTICIPATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTICIPATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARTICIPATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARTICIPATION):
    case SUCCESS(ACTION_TYPES.UPDATE_PARTICIPATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARTICIPATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/participations';

// Actions

export const getEntities: ICrudGetAllAction<IParticipationFp> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PARTICIPATION_LIST,
  payload: axios.get<IParticipationFp>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IParticipationFp> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARTICIPATION,
    payload: axios.get<IParticipationFp>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParticipationFp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARTICIPATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IParticipationFp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARTICIPATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParticipationFp> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARTICIPATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

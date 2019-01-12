import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserGroupFp, defaultValue } from 'app/shared/model/user-group-fp.model';

export const ACTION_TYPES = {
  FETCH_USERGROUP_LIST: 'userGroup/FETCH_USERGROUP_LIST',
  FETCH_USERGROUP: 'userGroup/FETCH_USERGROUP',
  CREATE_USERGROUP: 'userGroup/CREATE_USERGROUP',
  UPDATE_USERGROUP: 'userGroup/UPDATE_USERGROUP',
  DELETE_USERGROUP: 'userGroup/DELETE_USERGROUP',
  RESET: 'userGroup/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserGroupFp>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type UserGroupFpState = Readonly<typeof initialState>;

// Reducer

export default (state: UserGroupFpState = initialState, action): UserGroupFpState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERGROUP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USERGROUP):
    case REQUEST(ACTION_TYPES.UPDATE_USERGROUP):
    case REQUEST(ACTION_TYPES.DELETE_USERGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USERGROUP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERGROUP):
    case FAILURE(ACTION_TYPES.CREATE_USERGROUP):
    case FAILURE(ACTION_TYPES.UPDATE_USERGROUP):
    case FAILURE(ACTION_TYPES.DELETE_USERGROUP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERGROUP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERGROUP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERGROUP):
    case SUCCESS(ACTION_TYPES.UPDATE_USERGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERGROUP):
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

const apiUrl = 'api/user-groups';

// Actions

export const getEntities: ICrudGetAllAction<IUserGroupFp> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USERGROUP_LIST,
  payload: axios.get<IUserGroupFp>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IUserGroupFp> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERGROUP,
    payload: axios.get<IUserGroupFp>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUserGroupFp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERGROUP,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserGroupFp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERGROUP,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserGroupFp> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERGROUP,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

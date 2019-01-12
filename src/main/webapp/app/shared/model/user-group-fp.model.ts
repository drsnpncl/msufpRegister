import { IUser } from 'app/shared/model/user.model';

export interface IUserGroupFp {
  id?: number;
  groupName?: string;
  users?: IUser[];
}

export const defaultValue: Readonly<IUserGroupFp> = {};

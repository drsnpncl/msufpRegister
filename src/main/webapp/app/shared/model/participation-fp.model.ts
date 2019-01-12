import { Moment } from 'moment';

export interface IParticipationFp {
  id?: number;
  regDate?: Moment;
  paymentMode?: string;
  paymentAccept?: boolean;
  abstractSelect?: boolean;
  userGroupId?: number;
  eventId?: number;
}

export const defaultValue: Readonly<IParticipationFp> = {
  paymentAccept: false,
  abstractSelect: false
};

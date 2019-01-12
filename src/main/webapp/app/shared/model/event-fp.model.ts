export interface IEventFp {
  id?: number;
  name?: string;
  fee?: number;
  numberOfMembers?: string;
  typeOfParticipation?: string;
  freeEvent?: boolean;
  abstractSelect?: boolean;
  test?: boolean;
  status?: string;
  parentEvent?: string;
  eventType?: string;
}

export const defaultValue: Readonly<IEventFp> = {
  freeEvent: false,
  abstractSelect: false,
  test: false
};

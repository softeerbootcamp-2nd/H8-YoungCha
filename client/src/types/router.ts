import { Params } from 'react-router-dom';

export type MakingModeType = 'self' | 'guide';

export interface PathParamsType extends Params<string> {
  mode: MakingModeType;
  step: string;
  id: string;
}

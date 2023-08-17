import { PropsWithChildren } from 'react';
import DetailOptionHeader from './DetailOptionHeader';
import DetailOptionList from './DetailOptionList';
import DetailOptionTabs from './DetailOptionTabs';

interface DetailOptionProps {}

function DetailOption({ children }: PropsWithChildren<DetailOptionProps>) {
  return <div className="flex flex-col w-full gap-12px">{children}</div>;
}

export default Object.assign(DetailOption, {
  Header: DetailOptionHeader,
  List: DetailOptionList,
  Tabs: DetailOptionTabs,
});

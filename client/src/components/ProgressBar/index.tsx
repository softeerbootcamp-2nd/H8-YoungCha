import { Link, LinkProps } from 'react-router-dom';
import SelectedBar from './SelectedBar';

interface ProgressBarProps {
  step: number;
  mode: 'self' | 'guide';
  id: string;
}

interface ProgressItemProps extends LinkProps {
  active: boolean;
}

const PROGRESS_LIST = [
  '파워트레인',
  '구동 방식',
  '바디 타입',
  '외장 색상',
  '내장 색상',
  '휠 선택',
  '옵션 선택',
  '견적 내기',
];

function ProgressBar({ step, mode, id }: ProgressBarProps) {
  return (
    <nav className="relative z-10 text-center min-w-1024px h-26px title5">
      <span className="relative mx-auto whitespace-nowrap">
        <ProgressList {...{ step, mode, id }} />
        <SelectedBar active={step - 1} />
      </span>
      <div className="w-full h-0.5 absolute top-22px bg-grey-002" />
    </nav>
  );
}

function ProgressList({ step, mode, id }: ProgressBarProps) {
  return PROGRESS_LIST.map((item: string, index: number) => (
    <ProgressItem
      active={index + 1 === step}
      to={`/model/${id}/making/${mode}/${index + 1}`}
      key={`ProgressItem-${index}`}
    >
      {`${(index + 1).toString().padStart(2, '0')} ${item}`}
    </ProgressItem>
  ));
}

function ProgressItem({
  children,
  to,
  active = false,
  ...props
}: ProgressItemProps) {
  return (
    <Link
      to={to}
      className={`w-120px inline-block ${
        active ? 'text-main-blue font-medium' : 'text-grey-002'
      }`}
      {...props}
    >
      {children}
    </Link>
  );
}

export default ProgressBar;

import { Link, LinkProps } from 'react-router-dom';

interface ProgressBarProps {
  step: number;
  mode: 'self' | 'guide';
  id: string;
}

interface ProgressItemProps extends LinkProps {
  active: boolean;
}

interface SelectedBarProps {
  active: number;
  length: number;
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
    <nav className="z-10 text-center min-w-768px title5 border-b-2px border-grey-002 px-32px">
      <div className="max-w-5xl mx-auto xl:px-96px xl:max-w-none">
        <div className="flex h-full whitespace-nowrap">
          <ProgressList {...{ step, mode, id }} />
        </div>
        <SelectedBar active={step - 1} length={PROGRESS_LIST.length} />
      </div>
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
      className={`flex-1 flex justify-center items-center h-26px ${
        active ? 'text-main-blue font-medium' : 'text-grey-002'
      }`}
      {...props}
    >
      {children}
    </Link>
  );
}

function SelectedBar({ active, length }: SelectedBarProps) {
  return (
    <span
      className="z-10 flex text-center duration-300 ease-in-out bg-main-blue h-2px -mb-2px"
      style={{
        width: `${100 / length}%`,
        transform: `translateX(${active * 100}%)`,
      }}
    />
  );
}

export default ProgressBar;

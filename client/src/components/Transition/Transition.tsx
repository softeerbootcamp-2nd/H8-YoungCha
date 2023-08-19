import {
  useEffect,
  useLayoutEffect,
  useState,
  HTMLAttributes,
  PropsWithChildren,
} from 'react';

interface TransitionProps extends HTMLAttributes<HTMLElement> {
  render: boolean;
  base?: string;
  from?: string;
  to?: string;
  unmount?: boolean;
}

/**
 * @description
 * Transition 컴포넌트는 Tailwind CSS를 사용하여 transition을 적용하기 위한 컴포넌트입니다.
 * Tailwind CSS의 transition 클래스를 사용하여 transition을 적용합니다.
 * 기본적으로 transition duration은 300ms로 설정되어 있습니다.
 * transition 종류와 duration은 base props를 통해 설정할 수 있습니다.
 * transition 변경은 from, to props를 통해 설정할 수 있습니다.
 * transition 종료 후, unmount props가 true라면 컴포넌트를 unmount합니다.
 * @example
 * function Example() {
 *  const [render, setRender] = useState(false);
 *  return (
 *    <>
 *      <button onClick={() => setRender((value) => !value)}>click</button>
 *      <Transition
 *          className="flex items-center justify-center text-white w-100px h-100px bg-main-blue rounded-2xl"
 *          render={render}
 *          from="translate-x-50px opacity-0"
 *          to="translate-x-100px opacity-100"
 *          unmount
 *      >예시</Transition>
 *    </>
 *    );
 * };
 */

function Transition({
  children,
  className,
  base = 'transition duration-300',
  from = '',
  to = '',
  unmount,
  render,
  ...props
}: PropsWithChildren<TransitionProps>) {
  // mount 상태
  const [mount, setMount] = useState(render);

  // trnasition 클래스 이름
  const [transitionClassName, setTransitionClassName] = useState(from);

  // transition 종료 시, render 상태 변경
  const onTransitionEnd = () => {
    if (render) return;
    if (unmount) setMount(false);
  };

  // 초기 설정
  useEffect(() => {
    setMount(render);
    setTransitionClassName(from);
  }, []);

  // render 상태 변경시, unmount 상태라면 mount
  useLayoutEffect(() => {
    if (!mount) setMount(true);
  }, [render]);

  // render 상태 변경시, transition 클래스 이름 변경
  useEffect(() => {
    setTransitionClassName(render ? to : from);
  }, [render]);

  return (
    <>
      {mount && (
        <div
          className={[base, className, transitionClassName].join(' ')}
          onTransitionEnd={onTransitionEnd}
          {...props}
        >
          {children}
        </div>
      )}
    </>
  );
}

export default Transition;

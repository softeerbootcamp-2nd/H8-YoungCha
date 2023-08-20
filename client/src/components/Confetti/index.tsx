import {
  HTMLAttributes,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';
import { Confetto } from './confetto';
import { Sequin } from './sequin';

interface ConfettiProps extends HTMLAttributes<HTMLCanvasElement> {
  confettiCount?: number;
  sequinCount?: number;
}

// Canvas에 그릴 Confetto/Sequin objects를 저장하는 배열
const confetti: Confetto[] = [];
const sequins: Sequin[] = [];

function Confetti({
  className,
  confettiCount = 0,
  sequinCount = 0,
  ...props
}: ConfettiProps) {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  // animation ID는 unmount시 requestAnimationFrame을 취소하는 데 사용
  const [animationID, setAnimationID] = useState(0);

  const animate = useCallback(() => {
    const canvas = canvasRef.current!;
    const ctx = canvas.getContext('2d')!;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    confetti.forEach((confetto) => {
      const width = confetto.dimensions.x * confetto.scale.x;
      const height = confetto.dimensions.y * confetto.scale.y;

      // 캔버스에서 confetto의 위치와 회전을 설정
      ctx.translate(confetto.position.x, confetto.position.y);
      ctx.rotate(confetto.rotation);

      // confetto의 위치, 회전, 속도, 크기를 업데이트
      confetto.update();

      // confetto의 색상을 설정
      ctx.fillStyle =
        confetto.scale.y > 0 ? confetto.color.front : confetto.color.back;

      // confetto를 그리기
      ctx.fillRect(-width / 2, -height / 2, width, height);

      // transform matrix를 초기화
      ctx.setTransform(1, 0, 0, 1, 0, 0);
    });

    sequins.forEach((sequin) => {
      // 캔버스에서 sequin의 위치를 설정
      ctx.translate(sequin.position.x, sequin.position.y);

      // sequin의 위치, 회전, 속도, 크기를 업데이트
      sequin.update();

      // sequin의 색상을 설정
      ctx.fillStyle = sequin.color;

      // sequin을 그리기
      ctx.beginPath();
      ctx.arc(0, 0, sequin.radius, 0, 2 * Math.PI);
      ctx.fill();

      // transform matrix를 초기화
      ctx.setTransform(1, 0, 0, 1, 0, 0);
    });

    // 화면에서 떨어진 confetti와 sequin을 제거
    // confetti와 sequin을 제거하는 작업은 flickering을 방지하기 위해 따로 해야 함
    confetti.forEach((confetto, index) => {
      if (confetto.position.y >= canvas.height) confetti.splice(index, 1);
    });
    sequins.forEach((sequin, index) => {
      if (sequin.position.y >= canvas.height) sequins.splice(index, 1);
    });

    setAnimationID(requestAnimationFrame(animate));
  }, [canvasRef, setAnimationID]);

  useEffect(() => {
    const canvas = canvasRef.current!;
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    for (let i = 0; i < confettiCount; i++) {
      confetti.push(new Confetto(canvas));
    }
    for (let i = 0; i < sequinCount; i++) {
      sequins.push(new Sequin(canvas));
    }

    animate();

    return () => {
      cancelAnimationFrame(animationID);
    };
  }, []);

  return (
    <canvas
      ref={canvasRef}
      className={`absolute bottom-0 left-0 w-full pointer-events-none top-0 -z-10 ${className}`}
      {...props}
    />
  );
}

export default Confetti;

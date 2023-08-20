import { logger } from '@/utils';
import {
  HTMLAttributes,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';

interface ConfettiProps extends HTMLAttributes<HTMLCanvasElement> {}

function Confetti({ ...props }: ConfettiProps) {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  // animation ID는 unmount시 requestAnimationFrame을 취소하는 데 사용
  const [animationID, setAnimationID] = useState(0);

  const animate = useCallback(() => {
    const canvas = canvasRef.current!;
    const ctx = canvas.getContext('2d')!;

    logger(ctx);

    setAnimationID(requestAnimationFrame(animate));
  }, [canvasRef, setAnimationID]);

  useEffect(() => {
    const canvas = canvasRef.current!;
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    animate();

    return () => {
      cancelAnimationFrame(animationID);
    };
  }, []);

  return <canvas ref={canvasRef} {...props} />;
}

export default Confetti;

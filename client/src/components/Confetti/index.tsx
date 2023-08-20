import {
  HTMLAttributes,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';
import { Particle } from './particle';
import { CircleParticle } from './circle-particle';

interface ConfettiProps extends HTMLAttributes<HTMLCanvasElement> {
  confettiCount?: number;
  circlePrticleCount?: number;
}

// Canvas에 그릴 Particle/CircleParticle objects를 저장하는 배열
const confetti: Particle[] = [];
const circlePrticles: CircleParticle[] = [];

function Confetti({
  className = '',
  confettiCount = 0,
  circlePrticleCount = 0,
  ...props
}: ConfettiProps) {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  // animation ID는 unmount시 requestAnimationFrame을 취소하는 데 사용
  const [animationID, setAnimationID] = useState(0);

  const animate = useCallback(() => {
    const canvas = canvasRef.current!;
    const ctx = canvas.getContext('2d')!;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    confetti.forEach((particle) => {
      const width = particle.dimensions.x * particle.scale.x;
      const height = particle.dimensions.y * particle.scale.y;

      // 캔버스에서 particle의 위치와 회전을 설정
      ctx.translate(particle.position.x, particle.position.y);
      ctx.rotate(particle.rotation);

      // particle의 위치, 회전, 속도, 크기를 업데이트
      particle.update();

      // particle의 색상을 설정
      ctx.fillStyle =
        particle.scale.y > 0 ? particle.color.front : particle.color.back;

      // particle를 그리기
      ctx.fillRect(-width / 2, -height / 2, width, height);

      // transform matrix를 초기화
      ctx.setTransform(1, 0, 0, 1, 0, 0);
    });

    circlePrticles.forEach((circlePrticle) => {
      // 캔버스에서 circlePrticle의 위치를 설정
      ctx.translate(circlePrticle.position.x, circlePrticle.position.y);

      // circlePrticle의 위치, 회전, 속도, 크기를 업데이트
      circlePrticle.update();

      // circlePrticle의 색상을 설정
      ctx.fillStyle = circlePrticle.color;

      // circlePrticle을 그리기
      ctx.beginPath();
      ctx.arc(0, 0, circlePrticle.radius, 0, 2 * Math.PI);
      ctx.fill();

      // transform matrix를 초기화
      ctx.setTransform(1, 0, 0, 1, 0, 0);
    });

    // 화면에서 떨어진 Praticle들을 제거
    // Praticle들을 제거하는 작업은 flickering을 방지하기 위해 따로 해야 함
    confetti.forEach((particle, index) => {
      if (particle.position.y >= canvas.height) confetti.splice(index, 1);
    });
    circlePrticles.forEach((circlePrticle, index) => {
      if (circlePrticle.position.y >= canvas.height)
        circlePrticles.splice(index, 1);
    });

    setAnimationID(requestAnimationFrame(animate));
  }, [canvasRef, setAnimationID]);

  useEffect(() => {
    const canvas = canvasRef.current!;
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight - 108;

    for (let i = 0; i < confettiCount; i++) {
      confetti.push(new Particle(canvas));
    }
    for (let i = 0; i < circlePrticleCount; i++) {
      circlePrticles.push(new CircleParticle(canvas));
    }

    animate();

    return () => {
      cancelAnimationFrame(animationID);
    };
  }, []);

  return (
    <div className="absolute bottom-0 left-0 w-full pointer-events-none top-108px -z-10">
      <canvas ref={canvasRef} className={`-z-0 ${className}`} {...props} />
      <div className="absolute bottom-0 w-full h-1/2">
        <div className="h-2/3 bg-gradient-to-t-from-white-to-transparent"></div>
        <div className="bg-white h-1/3"></div>
      </div>
    </div>
  );
}

export default Confetti;

import {
  HTMLAttributes,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';
import { Particle } from './particle';
import { CircleParticle } from './circle-particle';

interface ConfettiProps extends HTMLAttributes<HTMLDivElement> {
  particleCount?: number;
  circleParticleCount?: number;
}

// Canvas에 그릴 Particle/CircleParticle objects를 저장하는 배열
const particles: Particle[] = [];
const circleParticles: CircleParticle[] = [];

function Confetti({
  className = '',
  particleCount = 0,
  circleParticleCount = 0,
  ...props
}: ConfettiProps) {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  // animation ID는 unmount시 requestAnimationFrame을 취소하는 데 사용
  const [animationID, setAnimationID] = useState(0);

  const animate = useCallback(() => {
    const canvas = canvasRef.current!;
    const ctx = canvas.getContext('2d')!;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    particles.forEach((particle) => {
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

    circleParticles.forEach((circleParticle) => {
      // 캔버스에서 circleParticle의 위치를 설정
      ctx.translate(circleParticle.position.x, circleParticle.position.y);

      // circleParticle의 위치, 회전, 속도, 크기를 업데이트
      circleParticle.update();

      // circleParticle의 색상을 설정
      ctx.fillStyle = circleParticle.color;

      // circleParticle을 그리기
      ctx.beginPath();
      ctx.arc(0, 0, circleParticle.radius, 0, 2 * Math.PI);
      ctx.fill();

      // transform matrix를 초기화
      ctx.setTransform(1, 0, 0, 1, 0, 0);
    });

    // 화면에서 떨어진 Praticle들을 제거
    // Praticle들을 제거하는 작업은 flickering을 방지하기 위해 따로 해야 함
    particles.forEach((particle, index) => {
      if (particle.position.y >= canvas.height) particles.splice(index, 1);
    });
    circleParticles.forEach((circleParticle, index) => {
      if (circleParticle.position.y >= canvas.height)
        circleParticles.splice(index, 1);
    });

    setAnimationID(requestAnimationFrame(animate));
  }, [canvasRef, setAnimationID]);

  useEffect(() => {
    const canvas = canvasRef.current!;
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight - 80;

    for (let i = 0; i < particleCount; i++) {
      particles.push(new Particle(canvas));
    }
    for (let i = 0; i < circleParticleCount; i++) {
      circleParticles.push(new CircleParticle(canvas));
    }

    animate();

    return () => {
      cancelAnimationFrame(animationID);
    };
  }, []);

  return (
    <div
      className={`absolute left-0 w-full pointer-events-none -z-10 ${className}`}
      {...props}
    >
      <canvas ref={canvasRef} className="-z-0" />
      <div className="absolute bottom-0 w-full h-1/2">
        <div className="h-2/3 bg-gradient-to-t-from-white-to-transparent"></div>
        <div className="bg-white h-1/3"></div>
      </div>
    </div>
  );
}

export default Confetti;

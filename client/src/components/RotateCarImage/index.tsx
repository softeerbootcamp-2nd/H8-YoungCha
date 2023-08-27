import useRotate from '@/hooks/useRotate.ts';
import { MouseEventHandler, useLayoutEffect, useState } from 'react';
import Spinner from '@/components/Spinner';

interface ImageRotatorProps {
  images: string[];
  className?: string;
}

function RotateCarImage({ images, className }: ImageRotatorProps) {
  const { state, handleMouseDown, handleMouseMove, handleMouseUp } =
    useRotate();
  const preventEventDefault: MouseEventHandler<HTMLDivElement> = (e) =>
    e.preventDefault();

  const [isImageLoading, setIsImageLoading] = useState(true);

  useLayoutEffect(() => {
    let count = 0;
    setIsImageLoading(true);
    images.forEach((src) => {
      const image = new Image();
      image.src = src;
      image.onload = () => {
        count += 1;
        setIsImageLoading(false);
      };
      image.onerror = () => {
        count += 1;
        if (count === images.length) {
          setIsImageLoading(false);
        }
      };
    });
  }, [images]);

  return (
    <>
      {isImageLoading ? (
        <div className={`flex items-center justify-center ${className}`}>
          <Spinner />
        </div>
      ) : (
        <div
          role="button"
          tabIndex={0}
          onMouseDown={handleMouseDown}
          onMouseMove={
            state.isMouseDown ? handleMouseMove : preventEventDefault
          }
          onMouseUp={handleMouseUp}
          onMouseLeave={handleMouseUp}
          className={`${
            state.isMouseDown ? 'cursor-grabbing' : 'cursor-grab'
          } relative w-full flex justify-center ${className}`}
        >
          <div className="relative flex items-center justify-center w-full">
            {images.map((imgSrc, idx) => (
              <img
                src={imgSrc}
                alt={imgSrc}
                key={idx}
                className={`${
                  idx === state.nextImgIdx ? 'block' : 'hidden'
                } pointer-events-none select-none`}
              />
            ))}
            <div className="absolute flex items-center justify-center text-white rounded-full top-16px right-16px opacity-30 body2 bg-grey-black w-40px h-40px">
              360°
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default RotateCarImage;

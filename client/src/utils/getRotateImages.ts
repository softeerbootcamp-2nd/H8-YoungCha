interface RotateImageType {
  url: string;
  count: number;
}

function getRotateImages({ url, count }: RotateImageType) {
  if (!url) return [];

  const imgUrl = url.split('colorchip-exterior.png')[0];
  return Array.from(
    { length: count },
    (_, i) => `${imgUrl}${String(i + 1).padStart(3, '0')}.png`
  );
}

export default getRotateImages;

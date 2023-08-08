interface TrimCardProps {
  children: React.ReactNode;
}

function TrimCard({ children }: TrimCardProps) {
  return <div>{children}</div>;
}

export default TrimCard;

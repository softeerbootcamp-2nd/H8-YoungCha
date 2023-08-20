interface OptionLayoutProps {
  title?: string;
  children?: React.ReactNode;
}

function OptionLayout({ title, children }: OptionLayoutProps) {
  return (
    <div className="flex flex-col max-w-5xl gap-16px">
      {title && (
        <h3 className="font-medium text-center text-grey-black py-8px">
          {title}
        </h3>
      )}
      <ul className="flex justify-around">{children}</ul>
    </div>
  );
}

export default OptionLayout;

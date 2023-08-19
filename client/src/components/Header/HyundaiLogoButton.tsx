import { Link } from 'react-router-dom';
import * as Icon from '../../assets/icons';

function HDLogoButton() {
  return (
    <Link to="/">
      <Icon.HDLogo className="w-45px md:w-166px h-23px" />
    </Link>
  );
}

export default HDLogoButton;

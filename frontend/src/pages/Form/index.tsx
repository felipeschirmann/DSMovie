import FormCard from "components/FormCard";
import { useParams } from "react-router-dom";
import { Movie } from "types/movie";

type Props = {
  movie: Movie;
}

function Form({movie}: Props ) {
  const params = useParams();
  return <FormCard movieId={`${params.movieId}`}/>;
}

export default Form;

namespace com.amaris.javatsinterop.api {
  class TestResource {

    constructor(private baseUrl : string) {}

    helloWorld(onSuccess : (data : string) => void) : void {
      $.ajax({
        url: this.baseUrl + "home/hello"
      }).then(onSuccess);
    }

    tree(onSuccess : (data : com.amaris.javatsinterop.dto.GenericNode) => void) : void {
      $.ajax({
        url: this.baseUrl + "home/tree"
      }).then(onSuccess);
    }

  }

}


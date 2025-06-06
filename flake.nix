{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs = {
    self,
    nixpkgs,
    fenix,
  } @ inputs: let
    system = "x86_64-linux";
    pkgs = import nixpkgs {inherit system;};
    lib = pkgs.lib;

    mcLibraries = with pkgs; [
      libpulseaudio
      libGL
      glfw
      openal
      stdenv.cc.cc.lib
      flite
    ];
  in {
    devShells.${system}.default = pkgs.mkShell {
      packages = with pkgs; [alejandra jdk] ++ mcLibraries;
      LD_LIBRARY_PATH = lib.makeLibraryPath mcLibraries;
    };
  };
}

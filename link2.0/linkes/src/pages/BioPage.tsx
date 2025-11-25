import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Plus, Trash2, GripVertical, Eye, Copy, Save, Image as ImageIcon, Palette } from 'lucide-react';
import { toast } from 'sonner';

interface BioLink {
  id: string;
  title: string;
  url: string;
}

export function BioPage() {
  const navigate = useNavigate();
  const [profileName, setProfileName] = useState('');
  const [bio, setBio] = useState('');
  const [links, setLinks] = useState<BioLink[]>([]);
  const [newLinkTitle, setNewLinkTitle] = useState('');
  const [newLinkUrl, setNewLinkUrl] = useState('');
  const [showPreview, setShowPreview] = useState(false);
  const [theme, setTheme] = useState<'gradient' | 'minimal' | 'dark'>('gradient');

  const addLink = () => {
    if (!newLinkTitle || !newLinkUrl) {
      toast.error('Preencha título e URL do link');
      return;
    }

    const newLink: BioLink = {
      id: Date.now().toString(),
      title: newLinkTitle,
      url: newLinkUrl
    };

    setLinks([...links, newLink]);
    setNewLinkTitle('');
    setNewLinkUrl('');
    toast.success('Link adicionado!');
  };

  const removeLink = (id: string) => {
    setLinks(links.filter(link => link.id !== id));
    toast.success('Link removido');
  };

  const copyBioPageUrl = () => {
    const url = `https://linkhub.io/${profileName.toLowerCase().replace(/\s+/g, '-')}`;
    navigator.clipboard.writeText(url);
    toast.success('URL copiada para área de transferência!');
  };

  const saveBioPage = () => {
    if (!profileName) {
      toast.error('Adicione um nome ao seu perfil');
      return;
    }
    toast.success('Bio Page salva com sucesso! 🎉');
  };

  const getThemeStyles = () => {
    switch (theme) {
      case 'gradient':
        return {
          bg: 'bg-gradient-to-br from-purple-500 via-pink-500 to-orange-500',
          linkBg: 'bg-white/90 backdrop-blur-sm text-gray-900 hover:bg-white',
          text: 'text-white'
        };
      case 'minimal':
        return {
          bg: 'bg-white',
          linkBg: 'bg-gray-100 text-gray-900 hover:bg-gray-200',
          text: 'text-gray-900'
        };
      case 'dark':
        return {
          bg: 'bg-gradient-to-br from-gray-900 to-gray-800',
          linkBg: 'bg-white/10 backdrop-blur-sm text-white hover:bg-white/20',
          text: 'text-white'
        };
    }
  };

  const themeStyles = getThemeStyles();

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-purple-50 to-pink-50">
      {/* Header */}
      <header className="bg-white border-b border-gray-200 sticky top-0 z-10 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex items-center justify-between">
            <button
              onClick={() => navigate('/dashboard')}
              className="flex items-center gap-2 text-gray-600 hover:text-gray-900 transition-colors"
            >
              <ArrowLeft className="w-5 h-5" />
              <span>Voltar</span>
            </button>
            <div className="flex items-center gap-3">
              <button
                onClick={() => setShowPreview(!showPreview)}
                className="flex items-center gap-2 px-4 py-2 bg-purple-100 text-purple-700 rounded-lg hover:bg-purple-200 transition-colors"
              >
                <Eye className="w-5 h-5" />
                {showPreview ? 'Ocultar' : 'Preview'}
              </button>
              <button
                onClick={saveBioPage}
                className="flex items-center gap-2 px-6 py-2 bg-gradient-to-r from-indigo-600 to-purple-600 text-white rounded-lg hover:shadow-lg transition-all"
              >
                <Save className="w-5 h-5" />
                Salvar
              </button>
            </div>
          </div>
        </div>
      </header>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="mb-6">
          <h2 className="text-gray-900 mb-2">Personalize sua Bio Page</h2>
          <p className="text-gray-600">Crie uma página única para todos os seus links importantes</p>
        </div>

        <div className={`grid gap-8 ${showPreview ? 'lg:grid-cols-2' : 'lg:grid-cols-1 max-w-3xl'}`}>
          {/* Editor */}
          <div className="space-y-6">
            {/* Profile Info Card */}
            <div className="bg-white rounded-2xl p-6 shadow-lg border border-gray-100">
              <h3 className="text-gray-900 mb-4">Informações do Perfil</h3>
              
              <div className="space-y-4">
                <div>
                  <label className="block mb-2 text-gray-700">Foto de Perfil</label>
                  <button className="w-full p-8 border-2 border-dashed border-gray-300 rounded-xl hover:border-purple-400 hover:bg-purple-50 transition-all">
                    <ImageIcon className="w-8 h-8 text-gray-400 mx-auto mb-2" />
                    <p className="text-sm text-gray-600">Clique para fazer upload</p>
                  </button>
                </div>

                <div>
                  <label className="block mb-2 text-gray-700">Nome/Título</label>
                  <input
                    type="text"
                    value={profileName}
                    onChange={(e) => setProfileName(e.target.value)}
                    placeholder="Seu nome ou marca"
                    className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-purple-500 focus:ring-2 focus:ring-purple-200 outline-none transition-all"
                  />
                </div>

                <div>
                  <label className="block mb-2 text-gray-700">Bio</label>
                  <textarea
                    value={bio}
                    onChange={(e) => setBio(e.target.value)}
                    placeholder="Conte um pouco sobre você..."
                    rows={3}
                    className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-purple-500 focus:ring-2 focus:ring-purple-200 outline-none transition-all resize-none"
                  />
                </div>

                <div>
                  <label className="block mb-2 text-gray-700">
                    <Palette className="w-4 h-4 inline mr-1" />
                    Tema
                  </label>
                  <div className="grid grid-cols-3 gap-3">
                    <button
                      onClick={() => setTheme('gradient')}
                      className={`p-4 rounded-xl border-2 transition-all ${
                        theme === 'gradient' ? 'border-purple-500 scale-105' : 'border-gray-200'
                      }`}
                    >
                      <div className="h-12 bg-gradient-to-r from-purple-500 to-pink-500 rounded-lg mb-2"></div>
                      <p className="text-sm text-gray-700">Gradiente</p>
                    </button>
                    <button
                      onClick={() => setTheme('minimal')}
                      className={`p-4 rounded-xl border-2 transition-all ${
                        theme === 'minimal' ? 'border-purple-500 scale-105' : 'border-gray-200'
                      }`}
                    >
                      <div className="h-12 bg-white border border-gray-300 rounded-lg mb-2"></div>
                      <p className="text-sm text-gray-700">Minimal</p>
                    </button>
                    <button
                      onClick={() => setTheme('dark')}
                      className={`p-4 rounded-xl border-2 transition-all ${
                        theme === 'dark' ? 'border-purple-500 scale-105' : 'border-gray-200'
                      }`}
                    >
                      <div className="h-12 bg-gray-900 rounded-lg mb-2"></div>
                      <p className="text-sm text-gray-700">Dark</p>
                    </button>
                  </div>
                </div>
              </div>
            </div>

            {/* Links Card */}
            <div className="bg-white rounded-2xl p-6 shadow-lg border border-gray-100">
              <h3 className="text-gray-900 mb-4">Gerenciar Links</h3>

              {/* Add Link Form */}
              <div className="space-y-3 mb-6 p-4 bg-purple-50 rounded-xl">
                <input
                  type="text"
                  value={newLinkTitle}
                  onChange={(e) => setNewLinkTitle(e.target.value)}
                  placeholder="Título do link"
                  className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-purple-500 focus:ring-2 focus:ring-purple-200 outline-none transition-all"
                />
                <input
                  type="url"
                  value={newLinkUrl}
                  onChange={(e) => setNewLinkUrl(e.target.value)}
                  placeholder="https://..."
                  className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-purple-500 focus:ring-2 focus:ring-purple-200 outline-none transition-all"
                />
                <button
                  onClick={addLink}
                  className="w-full px-6 py-3 bg-gradient-to-r from-indigo-600 to-purple-600 text-white rounded-xl hover:shadow-lg transition-all flex items-center justify-center gap-2"
                >
                  <Plus className="w-5 h-5" />
                  Adicionar Link
                </button>
              </div>

              {/* Links List */}
              {links.length > 0 ? (
                <div className="space-y-2">
                  {links.map((link) => (
                    <div
                      key={link.id}
                      className="flex items-center gap-3 p-4 bg-gray-50 rounded-xl group hover:bg-gray-100 transition-colors"
                    >
                      <GripVertical className="w-5 h-5 text-gray-400 cursor-move" />
                      <div className="flex-1 min-w-0">
                        <div className="text-gray-900">{link.title}</div>
                        <div className="text-sm text-gray-500 truncate">{link.url}</div>
                      </div>
                      <button
                        onClick={() => removeLink(link.id)}
                        className="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-colors opacity-0 group-hover:opacity-100"
                      >
                        <Trash2 className="w-4 h-4" />
                      </button>
                    </div>
                  ))}
                </div>
              ) : (
                <div className="text-center py-8 text-gray-400">
                  <Plus className="w-12 h-12 mx-auto mb-2 opacity-50" />
                  <p>Nenhum link adicionado ainda</p>
                </div>
              )}
            </div>

            {/* Share Card */}
            {profileName && (
              <div className="bg-gradient-to-r from-indigo-500 to-purple-500 rounded-2xl p-6 text-white shadow-lg">
                <h3 className="mb-3">Compartilhe sua Bio Page</h3>
                <div className="flex items-center gap-3 bg-white/20 backdrop-blur-sm rounded-xl p-3">
                  <code className="flex-1 text-sm">
                    linkhub.io/{profileName.toLowerCase().replace(/\s+/g, '-')}
                  </code>
                  <button
                    onClick={copyBioPageUrl}
                    className="p-2 bg-white/20 hover:bg-white/30 rounded-lg transition-colors"
                  >
                    <Copy className="w-5 h-5" />
                  </button>
                </div>
              </div>
            )}
          </div>

          {/* Preview */}
          {showPreview && (
            <div className="lg:sticky lg:top-24 h-fit">
              <div className="bg-white rounded-2xl p-6 shadow-lg border border-gray-100">
                <h3 className="text-gray-900 mb-4">Preview</h3>
                
                <div className="relative">
                  {/* Phone Frame */}
                  <div className="mx-auto w-full max-w-sm bg-gray-900 rounded-[3rem] p-3 shadow-2xl">
                    <div className="bg-white rounded-[2.5rem] overflow-hidden">
                      {/* Preview Content */}
                      <div className={`${themeStyles.bg} p-8 min-h-[600px]`}>
                        {profileName || bio || links.length > 0 ? (
                          <div className="text-center">
                            {/* Profile Avatar */}
                            <div className="w-24 h-24 bg-white/20 backdrop-blur-sm rounded-full mx-auto mb-4 flex items-center justify-center text-3xl">
                              {profileName ? profileName[0].toUpperCase() : '?'}
                            </div>

                            {profileName && (
                              <h2 className={`mb-2 ${themeStyles.text}`}>{profileName}</h2>
                            )}

                            {bio && (
                              <p className={`${themeStyles.text} opacity-90 mb-8 text-sm`}>{bio}</p>
                            )}

                            {/* Links */}
                            <div className="space-y-3">
                              {links.map((link) => (
                                <div
                                  key={link.id}
                                  className={`px-6 py-4 ${themeStyles.linkBg} rounded-xl transition-all text-center`}
                                >
                                  {link.title}
                                </div>
                              ))}
                            </div>
                          </div>
                        ) : (
                          <div className="flex items-center justify-center h-full text-white/50">
                            <div className="text-center">
                              <div className="text-6xl mb-4">🎨</div>
                              <p>Comece a criar</p>
                            </div>
                          </div>
                        )}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
